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
 * @return {EQiuyuanxinxi}
 */

export const QiuyuanxinxiCreateForm = () => {
    var route = unref(router.currentRoute);
    const userStore = useUserStore();
    const $session = userStore.session;
    if (!route.query) {
        route = useRoute();
    }
    const form = {
        zhanghao: "",
        mima: "",
        xingming: "",
        xingbie: "",
        lianxidianhua: "",
        nianling: "",
        shengao: "",
        tizhong: "",
        suoshuqiudui: "无",
        qiuyuanweizhi: "",
        qiuyihao: "",
        gerenjianjie: "",
        touxiang: "",
    };

    return form;
};

/**
 * 异步模式获取数据
 * @param id
 * @param readMap
 * @return {Promise<EQiuyuanxinxiForm>}
 */
export const canQiuyuanxinxiCreateForm = () => {
    return new Promise(async (resolve, reject) => {
        var form = QiuyuanxinxiCreateForm();
        resolve({ form });
    });
};

/**
 * 响应式获取球员信息 模块的表单字段数据
 * @return {EQiuyuanxinxiForm}
 */
export const useQiuyuanxinxiCreateForm = () => {
    const form = QiuyuanxinxiCreateForm();
    const formReactive = reactive(form);

    return { form: formReactive };
};

export const canQiuyuanxinxiSelect = (filter, result) => {
    http.post("/api/qiuyuanxinxi/selectPages").then((res) => {
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
export const useQiuyuanxinxiSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canQiuyuanxinxiSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<EQiuyuanxinxi>}
 */
export const canQiuyuanxinxiFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/qiuyuanxinxi/findById", { id }).then((res) => {
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
 * @return {EQiuyuanxinxi}
 */
export const useQiuyuanxinxiFindById = (id) => {
    var form = reactive({});

    canQiuyuanxinxiFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {EQiuyuanxinxi} data
 * @return {Promise<EResponseData<EQiuyuanxinxi>>}
 */
export const canQiuyuanxinxiInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/qiuyuanxinxi/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("qiuyuanxinxi_insert", res.data);
                        event.emit("qiuyuanxinxi_change", res.data);
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
 * @param {EQiuyuanxinxi} data
 * @return {Promise<EResponseData<EQiuyuanxinxi>>}
 */
export const canQiuyuanxinxiUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/qiuyuanxinxi/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("qiuyuanxinxi_update", res.data);
                        event.emit("qiuyuanxinxi_change", res.data);
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
export const canQiuyuanxinxiDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/qiuyuanxinxi/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("qiuyuanxinxi_delete", res.data);
                        event.emit("qiuyuanxinxi_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};
