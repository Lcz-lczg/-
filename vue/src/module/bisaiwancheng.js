import http from "@/utils/ajax/http";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores";
import { reactive, ref, unref } from "vue";
import rule from "@/utils/rule";
import { extend, isArray } from "@/utils/extend";
import { ElMessageBox } from "element-plus";
import router from "@/router";
import event from "@/utils/event";

import { canSaichengbiaoFindById } from "./saichengbiao";

/**
 * 响应式的对象数据
 * @return {EBisaiwancheng}
 */

export const BisaiwanchengCreateForm = () => {
    var route = unref(router.currentRoute);
    const userStore = useUserStore();
    const $session = userStore.session;
    if (!route.query) {
        route = useRoute();
    }
    const form = {
        bisaibianhao: "",
        bisaibiaoti: "",
        bisaileixing: "",
        qiuduia: "",
        qiuduib: "",
        wanchengbianhao: rule.getID(),
        shengliqiudui: "",
        wanchengneirong: "",
    };

    return form;
};

function exportForm(form, readMap) {
    var autoText = ["saichengbiaoid", "bisaibianhao", "bisaibiaoti", "bisaileixing", "qiuduia", "qiuduib"];
    for (var txt of autoText) {
        form[txt] = readMap[txt];
    }
}

/**
 * 异步模式获取数据
 * @param id
 * @param readMap
 * @return {Promise<EBisaiwanchengForm>}
 */
export const canBisaiwanchengCreateForm = (id, readMap) => {
    return new Promise(async (resolve, reject) => {
        var form = BisaiwanchengCreateForm();
        if (!readMap || !readMap.id) {
            readMap = await canSaichengbiaoFindById(id).catch(reject);
        }
        exportForm(form, readMap);
        form.saichengbiaoid = readMap.id;
        resolve({ form, readMap });
    });
};

/**
 * 响应式获取比赛完成 模块的表单字段数据
 * @return {EBisaiwanchengForm}
 */
export const useBisaiwanchengCreateForm = (id) => {
    const form = BisaiwanchengCreateForm();
    const formReactive = reactive(form);

    const readMap = reactive({});
    canSaichengbiaoFindById(id).then(
        (map) => {
            exportForm(formReactive, map);
            extend(readMap, map);
            formReactive.saichengbiaoid = map.id;
        },
        (err) => {
            ElMessageBox.alert(err.message);
        }
    );
    return { form: formReactive, readMap };
};

export const canBisaiwanchengSelect = (filter, result) => {
    http.post("/api/bisaiwancheng/selectPages").then((res) => {
        if (res.code == 0) {
            extend(result, res.data);
        } else {
            ElMessageBox.alert(res.msg);
        }
    });
};

/**
 * 获取分页数据
 * @param filter
 */
export const useBisaiwanchengSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canBisaiwanchengSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<EBisaiwancheng>}
 */
export const canBisaiwanchengFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/bisaiwancheng/findById", { id }).then((res) => {
            if (res.code == 0) {
                resolve(res.data);
            } else {
                reject(new Error(res.msg));
            }
        }, reject);
    });
};

/**
 * 根据id 获取一行数据
 * @param id
 * @return {EBisaiwancheng}
 */
export const useBisaiwanchengFindById = (id) => {
    var form = reactive({});

    canBisaiwanchengFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {EBisaiwancheng} data
 * @return {Promise<EResponseData<EBisaiwancheng>>}
 */
export const canBisaiwanchengInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/bisaiwancheng/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaiwancheng_insert", res.data);
                        event.emit("bisaiwancheng_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};

/**
 * 根据数据更新数据库
 * @param {EBisaiwancheng} data
 * @return {Promise<EResponseData<EBisaiwancheng>>}
 */
export const canBisaiwanchengUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/bisaiwancheng/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaiwancheng_update", res.data);
                        event.emit("bisaiwancheng_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};

/**
 * 根据id 或者列表id
 * @param {number|number[]} id
 * @return {Promise<EResponseData<string>>}
 */
export const canBisaiwanchengDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/bisaiwancheng/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaiwancheng_delete", res.data);
                        event.emit("bisaiwancheng_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};
