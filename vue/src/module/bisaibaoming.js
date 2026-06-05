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
 * @return {EBisaibaoming}
 */

export const BisaibaomingCreateForm = () => {
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
        baomingbianhao: rule.getID(),
        baomingqiudui: "",
        qiuduibianhao: "",
        qiuduimingcheng: "",
        qiuduiduiyuan: "",
        jiaolianxingming: $session.xingming,
        qiuduijiaolian: $session.username,
        lianxifangshi: $session.lianxidianhua,
        beizhu: "",
        zhuangtai: "已报名",
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
 * @return {Promise<EBisaibaomingForm>}
 */
export const canBisaibaomingCreateForm = (id, readMap) => {
    return new Promise(async (resolve, reject) => {
        var form = BisaibaomingCreateForm();
        if (!readMap || !readMap.id) {
            readMap = await canBisaixinxiFindById(id).catch(reject);
        }
        exportForm(form, readMap);
        form.bisaixinxiid = readMap.id;
        resolve({ form, readMap });
    });
};

/**
 * 响应式获取比赛报名 模块的表单字段数据
 * @return {EBisaibaomingForm}
 */
export const useBisaibaomingCreateForm = (id) => {
    const form = BisaibaomingCreateForm();
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

export const canBisaibaomingSelect = (filter, result) => {
    http.post("/api/bisaibaoming/selectPages").then((res) => {
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
export const useBisaibaomingSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canBisaibaomingSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<EBisaibaoming>}
 */
export const canBisaibaomingFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/bisaibaoming/findById", { id }).then((res) => {
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
 * @return {EBisaibaoming}
 */
export const useBisaibaomingFindById = (id) => {
    var form = reactive({});

    canBisaibaomingFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {EBisaibaoming} data
 * @return {Promise<EResponseData<EBisaibaoming>>}
 */
export const canBisaibaomingInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/bisaibaoming/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaibaoming_insert", res.data);
                        event.emit("bisaibaoming_change", res.data);
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
 * @param {EBisaibaoming} data
 * @return {Promise<EResponseData<EBisaibaoming>>}
 */
export const canBisaibaomingUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/bisaibaoming/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaibaoming_update", res.data);
                        event.emit("bisaibaoming_change", res.data);
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
export const canBisaibaomingDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/bisaibaoming/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaibaoming_delete", res.data);
                        event.emit("bisaibaoming_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};
