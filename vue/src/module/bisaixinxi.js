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
 * @return {EBisaixinxi}
 */

export const BisaixinxiCreateForm = () => {
    var route = unref(router.currentRoute);
    const userStore = useUserStore();
    const $session = userStore.session;
    if (!route.query) {
        route = useRoute();
    }
    const form = {
        bisaibianhao: rule.getID(),
        bisaibiaoti: "",
        bisaileixing: "",
        bisaimoshi: "",
        bisaitupian: "",
        canjiaqiudui: "",
        bisaichangdi: "",
        bisaineirong: "",
        tianjiaren: $session.username,
    };

    return form;
};

/**
 * 异步模式获取数据
 * @param id
 * @param readMap
 * @return {Promise<EBisaixinxiForm>}
 */
export const canBisaixinxiCreateForm = () => {
    return new Promise(async (resolve, reject) => {
        var form = BisaixinxiCreateForm();
        resolve({ form });
    });
};

/**
 * 响应式获取比赛信息 模块的表单字段数据
 * @return {EBisaixinxiForm}
 */
export const useBisaixinxiCreateForm = () => {
    const form = BisaixinxiCreateForm();
    const formReactive = reactive(form);

    return { form: formReactive };
};

export const canBisaixinxiSelect = (filter, result) => {
    http.post("/api/bisaixinxi/selectPages").then((res) => {
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
export const useBisaixinxiSelect = (filter) => {
    const result = reactive({
        lists: [],
        total: {},
    });
    canBisaixinxiSelect(filter, result);
    return result;
};

/**
 * 根据
 * @param id
 * @return {Promise<EBisaixinxi>}
 */
export const canBisaixinxiFindById = (id) => {
    return new Promise((resolve, reject) => {
        // 读取后台数据
        http.get("/api/bisaixinxi/findById", { id }).then((res) => {
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
 * @return {EBisaixinxi}
 */
export const useBisaixinxiFindById = (id) => {
    var form = reactive({});

    canBisaixinxiFindById(id).then((res) => {
        extend(form, res);
    });
    return form;
};

/**
 * 根据数据,插入到数据库中
 * @param {EBisaixinxi} data
 * @return {Promise<EResponseData<EBisaixinxi>>}
 */
export const canBisaixinxiInsert = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/bisaixinxi/insert", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaixinxi_insert", res.data);
                        event.emit("bisaixinxi_change", res.data);
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
 * @param {EBisaixinxi} data
 * @return {Promise<EResponseData<EBisaixinxi>>}
 */
export const canBisaixinxiUpdate = (data) => {
    return new Promise((resolve, reject) => {
        http.post("/api/bisaixinxi/update", data)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaixinxi_update", res.data);
                        event.emit("bisaixinxi_change", res.data);
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
export const canBisaixinxiDelete = (id) => {
    var res = [];
    if (!isArray(id)) {
        res.push(id);
    } else {
        res = id;
    }

    return new Promise((resolve, reject) => {
        http.post("/api/bisaixinxi/delete", res)
            .json()
            .then(
                (res) => {
                    resolve(res);
                    if (res.code == 0) {
                        event.emit("bisaixinxi_delete", res.data);
                        event.emit("bisaixinxi_change", res.data);
                    }
                },
                (err) => {
                    reject(err);
                }
            );
    });
};

export const useBisaixinxidianzan = (id, iss, count) => {
    const is_dianzan = iss ? iss : ref(false);
    const dianzanCount = count ? count : ref(0);

    http.get("/api/bisaixinxi/getdianzan", { id }).then((res) => {
        if (res.code == 0) {
            is_dianzan.value = res.data.is_dianzan;
            dianzanCount.value = res.data.dianzanCount;
        }
    });

    return { is_dianzan, dianzanCount };
};
