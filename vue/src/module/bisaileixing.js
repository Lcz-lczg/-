import http from "@/utils/ajax/http";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores";
import { reactive, ref, unref } from "vue";
import rule from "@/utils/rule";
import { extend, isArray } from "@/utils/extend";
import { ElMessageBox } from "element-plus";
import router from "@/router";
import event from "@/utils/event";

/**
 * 响应式的对象数据
 * @return {EBisaileixing}
 */

export const BisaileixingCreateForm = () => {
    var route = unref(router.currentRoute);
    const userStore = useUserStore();
    const $session = userStore.session;
    if (!route.query) {
        route = useRoute();
    }
    const form = {
        leixingmingcheng: "",
    };

    return form;
};

/**
 * 异步模式获取数据
 * @param id
 * @param readMap
 * @return {Promise<EBisaileixingForm>}
 */
export const canBisaileixingCreateForm = () => {
    return new Promise(async (resolve, reject) => {
        var form = BisaileixingCreateForm();
        resolve({ form });
    });
};

/**
 * 响应式获取比赛类型 模块的表单字段数据
 * @return {EBisaileixingForm}
 */
export const useBisaileixingCreateForm = () => {
    const form = BisaileixingCreateForm();
    const formReactive = reactive(form);

    return { form: formReactive };
};

export const canBisaileixingSelect = (filter, result) => {
    http.post("/api/bisaileixing/selectPages").then((res) => {
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
export const useBisaileixingSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canBisaileixingSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<EBisaileixing>}
 */
export const canBisaileixingFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/bisaileixing/findById", { id }).then((res) => {
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
 * @return {EBisaileixing}
 */
export const useBisaileixingFindById = (id) => {
    var form = reactive({});

    canBisaileixingFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {EBisaileixing} data
 * @return {Promise<EResponseData<EBisaileixing>>}
 */
export const canBisaileixingInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/bisaileixing/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaileixing_insert", res.data);
                        event.emit("bisaileixing_change", res.data);
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
 * @param {EBisaileixing} data
 * @return {Promise<EResponseData<EBisaileixing>>}
 */
export const canBisaileixingUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/bisaileixing/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaileixing_update", res.data);
                        event.emit("bisaileixing_change", res.data);
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
export const canBisaileixingDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/bisaileixing/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaileixing_delete", res.data);
                        event.emit("bisaileixing_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};
