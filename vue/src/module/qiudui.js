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
 * @return {EQiudui}
 */

export const QiuduiCreateForm = () => {
    var route = unref(router.currentRoute);
    const userStore = useUserStore();
    const $session = userStore.session;
    if (!route.query) {
        route = useRoute();
    }
    const form = {
        qiuduibianhao: rule.getID(),
        qiuduimingcheng: "",
        qiuduitupian: "",
        qiuduiduiyuan: "",
        shilidengji: "",
        qiuduijifen: "",
        qiuduixiangqing: "",
        jiaolianxingming: $session.xingming,
        qiuduijiaolian: $session.username,
        chenglishijian: "",
    };

    return form;
};

/**
 * 异步模式获取数据
 * @param id
 * @param readMap
 * @return {Promise<EQiuduiForm>}
 */
export const canQiuduiCreateForm = () => {
    return new Promise(async (resolve, reject) => {
        var form = QiuduiCreateForm();
        resolve({ form });
    });
};

/**
 * 响应式获取球队 模块的表单字段数据
 * @return {EQiuduiForm}
 */
export const useQiuduiCreateForm = () => {
    const form = QiuduiCreateForm();
    const formReactive = reactive(form);

    return { form: formReactive };
};

export const canQiuduiSelect = (filter, result) => {
    http.post("/api/qiudui/selectPages").then((res) => {
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
export const useQiuduiSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canQiuduiSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<EQiudui>}
 */
export const canQiuduiFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/qiudui/findById", { id }).then((res) => {
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
 * @return {EQiudui}
 */
export const useQiuduiFindById = (id) => {
    var form = reactive({});

    canQiuduiFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {EQiudui} data
 * @return {Promise<EResponseData<EQiudui>>}
 */
export const canQiuduiInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/qiudui/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("qiudui_insert", res.data);
                        event.emit("qiudui_change", res.data);
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
 * @param {EQiudui} data
 * @return {Promise<EResponseData<EQiudui>>}
 */
export const canQiuduiUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/qiudui/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("qiudui_update", res.data);
                        event.emit("qiudui_change", res.data);
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
export const canQiuduiDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/qiudui/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("qiudui_delete", res.data);
                        event.emit("qiudui_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};
