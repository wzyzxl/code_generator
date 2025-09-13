import { databaseConnections } from "@/apis/database";
import { useDatabaseStore } from "@/stores/database";
import type RequestType from "@/types/request_type";
import { ElMessage } from "element-plus";

export default function () {
  /**
   * 验证保存配置（是否符合格式）
   * @param form 表单数据
   * @returns 验证情况
   */
  function verifyAndSaveConfiguration(form: RequestType): boolean {
    // 验证必填字段
    if (!form.databaseType) {
      ElMessage.error('数据库类型不能为空');
      return false;
    }
    if (!form.host) {
      ElMessage.error('主机地址不能为空');
      return false;
    }
    if (!form.port) {
      ElMessage.error('端口号不能为空');
      return false;
    }
    if (!form.databaseName) {
      ElMessage.error('数据库名不能为空');
      return false;
    }
    if (!form.username) {
      ElMessage.error('用户名不能为空');
      return false;
    }
    if (!form.password) {
      ElMessage.error('密码不能为空');
      return false;
    }

    // 验证主机地址格式 (localhost、IPv4或IPv6)
    if (form.host) {
      // localhost 检查
      const isLocalhost = form.host.trim().toLowerCase() === 'localhost';

      // IPv4 正则表达式
      const ipv4Regex = /^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

      // IPv6 正则表达式 (简化版)
      const ipv6Regex = /^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$/;

      if (!isLocalhost && !ipv4Regex.test(form.host) && !ipv6Regex.test(form.host)) {
        ElMessage.error('主机地址必须是localhost、IPv4或IPv6格式');
        return false;
      }
    }

    // 验证端口号规则 (1-65535之间的整数)
    if (form.port !== undefined && form.port !== null) {
      if (form.port < 1 || form.port > 65535) {
        ElMessage.error('端口号必须是1-65535之间的整数');
        return false;
      }
    }
    return true;
  }

  /**
   * 判断连接信息是否改变
   * @param form 表单
   * @returns 改变与否
   */
  function isBaseInfoChanged(form: RequestType): boolean {
    return form.databaseId != useDatabaseStore().databaseId ||
      form.databaseType != useDatabaseStore().databaseType ||
      form.host != useDatabaseStore().host ||
      form.port != useDatabaseStore().port ||
      form.databaseName != useDatabaseStore().databaseName ||
      form.username != useDatabaseStore().username ||
      form.password != useDatabaseStore().password;
  }

  /**
   * 连接测试
   * @param form 表单元素
   * @param showSuccessTip 是否显示成功提示信息
   * @returns 连接成功与否
   */
  async function connections(form: RequestType, showSuccessTip: boolean = false): Promise<boolean> {
    // 验证是否符合规则
    const verifyResult: boolean = verifyAndSaveConfiguration(form);

    // 当基本信息改变时候，允许重新生成数据库连接
    if (isBaseInfoChanged(form)) {
      form.changeFlag = true;
      form.databaseId = null
    }

    // 规则通过，切允许重新连接
    if (verifyResult) {
      try {
        const res = await databaseConnections(form);
        const result: { state: boolean, databaseId: number } = res.data.data;
        // 验证接口状态码
        if (res.data.code !== 200 || !result.state) {
          ElMessage.error("连接数据库出错，错误信息：" + (res.data.message || "未知错误"));
          return false;
        }

        // 修改基本信息
        form.databaseId = result.databaseId;
        form.changeFlag = false;

        useDatabaseStore().databaseType = form.databaseType;
        useDatabaseStore().databaseId = form.databaseId;
        useDatabaseStore().host = form.host;
        useDatabaseStore().port = form.port;
        useDatabaseStore().databaseName = form.databaseName;
        useDatabaseStore().username = form.username;
        useDatabaseStore().password = form.password;
        if (showSuccessTip) {
          ElMessage.success('连接成功');
        }
        return true;
      } catch (error) {
        ElMessage.error("连接出错：" + error);
        return false;
      }
    } else if (!verifyResult) {
      return false;
    } else {
      ElMessage.success("连接成功");
      return true;
    }
  }

  return { connections };
}
