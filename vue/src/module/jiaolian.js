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
 * @return {EJiaolian}
 */

export const JiaolianCreateForm = () => {
    var route = unref(router.currentRoute);
    const userStore = useUserStore();
    const $session = userStore.session;
    if (!route.query) {
        route = useRoute();
    }
    const form = {
        gonghao: "",
        mima: "",
        zhicheng: "",
        xingming: "",
        xingbie: "",
        zizhizhengshu: "",
        zhijiaojingyan: "",
        lianxidianhua: "",
        touxiang: "",

        issh: "否",
    };

    return form;
};

/**
 * 异步模式获取数据
 * @param id
 * @param readMap
 * @return {Promise<EJiaolianForm>}
 */
export const canJiaolianCreateForm = () => {
    return new Promise(async (resolve, reject) => {
        var form = JiaolianCreateForm();
        resolve({ form });
    });
};

/**
 * 响应式获取教练 模块的表单字段数据
 * @return {EJiaolianForm}
 */
export const useJiaolianCreateForm = () => {
    const form = JiaolianCreateForm();
    const formReactive = reactive(form);

    return { form: formReactive };
};

export const canJiaolianSelect = (filter, result) => {
    http.post("/api/jiaolian/selectPages").then((res) => {
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
export const useJiaolianSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canJiaolianSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<EJiaolian>}
 */
export const canJiaolianFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/jiaolian/findById", { id }).then((res) => {
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
 * @return {EJiaolian}
 */
export const useJiaolianFindById = (id) => {
    var form = reactive({});

    canJiaolianFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {EJiaolian} data
 * @return {Promise<EResponseData<EJiaolian>>}
 */
export const canJiaolianInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/jiaolian/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("jiaolian_insert", res.data);
                        event.emit("jiaolian_change", res.data);
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
 * @param {EJiaolian} data
 * @return {Promise<EResponseData<EJiaolian>>}
 */
export const canJiaolianUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/jiaolian/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("jiaolian_update", res.data);
                        event.emit("jiaolian_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};

/**
 * 审核是否上架
 * @param {EJiaolian} data
 * @return {Promise<EResponseData<EJiaolian>>}
 */
export const canJiaolianCheckIssh = (row) => {
    return new Promise((resolve, reject) => {
        var id = row.id;
        var value = row.issh === "是" ? "否" : "是";
        http.get("/api/jiaolian/checkIssh", { id, value })
            .json()
            .then(
                (res) => {
                    if (res.code == 0) {
                        row.issh = value;
                    }
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("jiaolian_update", row);
                        event.emit("jiaolian_change", row);
                    }
                },
                (err) => {
                    ElMessageBox.alert(err.message);
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
export const canJiaolianDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/jiaolian/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("jiaolian_delete", res.data);
                        event.emit("jiaolian_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};
