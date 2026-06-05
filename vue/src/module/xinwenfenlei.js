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
 * @return {EXinwenfenlei}
 */

export const XinwenfenleiCreateForm = () => {
    var route = unref(router.currentRoute);
    const userStore = useUserStore();
    const $session = userStore.session;
    if (!route.query) {
        route = useRoute();
    }
    const form = {
        fenleimingcheng: "",
    };

    return form;
};

/**
 * 异步模式获取数据
 * @param id
 * @param readMap
 * @return {Promise<EXinwenfenleiForm>}
 */
export const canXinwenfenleiCreateForm = () => {
    return new Promise(async (resolve, reject) => {
        var form = XinwenfenleiCreateForm();
        resolve({ form });
    });
};

/**
 * 响应式获取新闻分类 模块的表单字段数据
 * @return {EXinwenfenleiForm}
 */
export const useXinwenfenleiCreateForm = () => {
    const form = XinwenfenleiCreateForm();
    const formReactive = reactive(form);

    return { form: formReactive };
};

export const canXinwenfenleiSelect = (filter, result) => {
    http.post("/api/xinwenfenlei/selectPages").then((res) => {
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
export const useXinwenfenleiSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canXinwenfenleiSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<EXinwenfenlei>}
 */
export const canXinwenfenleiFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/xinwenfenlei/findById", { id }).then((res) => {
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
 * @return {EXinwenfenlei}
 */
export const useXinwenfenleiFindById = (id) => {
    var form = reactive({});

    canXinwenfenleiFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {EXinwenfenlei} data
 * @return {Promise<EResponseData<EXinwenfenlei>>}
 */
export const canXinwenfenleiInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/xinwenfenlei/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("xinwenfenlei_insert", res.data);
                        event.emit("xinwenfenlei_change", res.data);
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
 * @param {EXinwenfenlei} data
 * @return {Promise<EResponseData<EXinwenfenlei>>}
 */
export const canXinwenfenleiUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/xinwenfenlei/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("xinwenfenlei_update", res.data);
                        event.emit("xinwenfenlei_change", res.data);
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
export const canXinwenfenleiDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/xinwenfenlei/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("xinwenfenlei_delete", res.data);
                        event.emit("xinwenfenlei_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};
