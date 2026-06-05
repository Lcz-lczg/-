import http from "@/utils/ajax/http";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores";
import { reactive, ref, unref } from "vue";
import rule from "@/utils/rule";
import { extend, isArray } from "@/utils/extend";
import { ElMessageBox } from "element-plus";
import router from "@/router";
import event from "@/utils/event";

import { canBisaixinxiFindById } from "./bisaixinxi";

/**
 * 响应式的对象数据
 * @return {ESaichengbiao}
 */

export const SaichengbiaoCreateForm = () => {
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
        vs: "",
        qiuduib: "",
        bifen: "",
        zhuangtai: "待开始",
    };

    return form;
};

function exportForm(form, readMap) {
    var autoText = ["bisaixinxiid", "bisaibianhao", "bisaibiaoti", "bisaileixing"];
    for (var txt of autoText) {
        form[txt] = readMap[txt];
    }
}

/**
 * 异步模式获取数据
 * @param id
 * @param readMap
 * @return {Promise<ESaichengbiaoForm>}
 */
export const canSaichengbiaoCreateForm = (id, readMap) => {
    return new Promise(async (resolve, reject) => {
        var form = SaichengbiaoCreateForm();
        if (!readMap || !readMap.id) {
            readMap = await canBisaixinxiFindById(id).catch(reject);
        }
        exportForm(form, readMap);
        form.bisaixinxiid = readMap.id;
        resolve({ form, readMap });
    });
};

/**
 * 响应式获取赛程表 模块的表单字段数据
 * @return {ESaichengbiaoForm}
 */
export const useSaichengbiaoCreateForm = (id) => {
    const form = SaichengbiaoCreateForm();
    const formReactive = reactive(form);

    const readMap = reactive({});
    canBisaixinxiFindById(id).then(
        (map) => {
            exportForm(formReactive, map);
            extend(readMap, map);
            formReactive.bisaixinxiid = map.id;
        },
        (err) => {
            ElMessageBox.alert(err.message);
        }
    );
    return { form: formReactive, readMap };
};

export const canSaichengbiaoSelect = (filter, result) => {
    http.post("/api/saichengbiao/selectPages").then((res) => {
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
export const useSaichengbiaoSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canSaichengbiaoSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<ESaichengbiao>}
 */
export const canSaichengbiaoFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/saichengbiao/findById", { id }).then((res) => {
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
 * @return {ESaichengbiao}
 */
export const useSaichengbiaoFindById = (id) => {
    var form = reactive({});

    canSaichengbiaoFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {ESaichengbiao} data
 * @return {Promise<EResponseData<ESaichengbiao>>}
 */
export const canSaichengbiaoInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/saichengbiao/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("saichengbiao_insert", res.data);
                        event.emit("saichengbiao_change", res.data);
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
 * @param {ESaichengbiao} data
 * @return {Promise<EResponseData<ESaichengbiao>>}
 */
export const canSaichengbiaoUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/saichengbiao/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("saichengbiao_update", res.data);
                        event.emit("saichengbiao_change", res.data);
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
export const canSaichengbiaoDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/saichengbiao/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("saichengbiao_delete", res.data);
                        event.emit("saichengbiao_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};
