import { useUserStore } from "@/stores/user";

import guanliyuan from "./guanliyuan";
import guanzhong from "./guanzhong";
import jiaolian from "./jiaolian";
import qiuyuanxinxi from "./qiuyuanxinxi";

export function getMenus() {
    return new Promise((resolve, reject) => {
        var userStore = useUserStore();
        var cx = userStore.getSession("cx");
        if (cx == "管理员") {
            resolve(guanliyuan);
        }
        if (cx == "观众") {
            resolve(guanzhong);
        }
        if (cx == "教练") {
            resolve(jiaolian);
        }
        if (cx == "球员信息") {
            resolve(qiuyuanxinxi);
        }
    });
}

export default {
    getMenus,
};
