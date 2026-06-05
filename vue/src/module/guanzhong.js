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
 * @return {EGuanzhong}
 */

export const GuanzhongCreateForm = () => {
    var route = unref(router.currentRoute);
    const userStore = useUserStore();
    const $session = userStore.session;
    if (!route.query) {
        route = useRoute();
    }
    const form = {
        yonghuming: "",
        mima: "",
        xingming: "",
        xingbie: "",
        shouji: "",
        youxiang: "",
        touxiang: "",

        issh: "否",
    };

    return form;
};

/**
 * 异步模式获取数据
 * @param id
 * @param readMap
 * @return {Promise<EGuanzhongForm>}
 */
export const canGuanzhongCreateForm = () => {
    return new Promise(async (resolve, reject) => {
        var form = GuanzhongCreateForm();
        resolve({ form });
    });
};

/**
 * 响应式获取观众 模块的表单字段数据
 * @return {EGuanzhongForm}
 */
export const useGuanzhongCreateForm = () => {
    const form = GuanzhongCreateForm();
    const formReactive = reactive(form);

    return { form: formReactive };
};

export const canGuanzhongSelect = (filter, result) => {
    http.post("/api/guanzhong/selectPages").then((res) => {
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
export const useGuanzhongSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canGuanzhongSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<EGuanzhong>}
 */
export const canGuanzhongFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/guanzhong/findById", { id }).then((res) => {
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
 * @return {EGuanzhong}
 */
export const useGuanzhongFindById = (id) => {
    var form = reactive({});

    canGuanzhongFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {EGuanzhong} data
 * @return {Promise<EResponseData<EGuanzhong>>}
 */
export const canGuanzhongInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/guanzhong/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("guanzhong_insert", res.data);
                        event.emit("guanzhong_change", res.data);
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
 * @param {EGuanzhong} data
 * @return {Promise<EResponseData<EGuanzhong>>}
 */
export const canGuanzhongUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/guanzhong/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("guanzhong_update", res.data);
                        event.emit("guanzhong_change", res.data);
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
 * @param {EGuanzhong} data
 * @return {Promise<EResponseData<EGuanzhong>>}
 */
export const canGuanzhongCheckIssh = (row) => {
    return new Promise((resolve, reject) => {
        var id = row.id;
        var value = row.issh === "是" ? "否" : "是";
        http.get("/api/guanzhong/checkIssh", { id, value })
            .json()
            .then(
                (res) => {
                    if (res.code == 0) {
                        row.issh = value;
                    }
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("guanzhong_update", row);
                        event.emit("guanzhong_change", row);
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
export const canGuanzhongDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/guanzhong/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("guanzhong_delete", res.data);
                        event.emit("guanzhong_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};
