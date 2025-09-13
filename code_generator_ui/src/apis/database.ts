import type RequestType from "@/types/request_type";
import type ResponseType from "@/types/response_type";
import httpInstance from "@/utils/http";
import type { AxiosResponse } from "axios";

/**
 * 获取数据库类型
 * @returns 数据库类型
 */
export const getDatabaseType = async (): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: '/database/types',
    method: 'get'
  });
}

/**
 * 测试连接
 * @param form 表单数据
 * @returns 连接情况
 */
export const databaseConnections = async (form: RequestType): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: 'database/connections',
    method: 'post',
    data: form
  });
}

/**
 * 获取数据库中模式
 * @param form 表单数据
 * @returns 模式
 */
export const queryDatabasePattern = async (form: RequestType): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: 'database/patterns',
    method: 'post',
    data: form
  });
}

/**
 * 获取数据表名称
 * @param form 表单数据
 * @returns 表名
 */
export const queryDatabaseTable = async (form: RequestType): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: 'database/tables',
    method: 'post',
    data: form
  });
}

/**
 * 保存配置信息
 * @param form 表单元素
 * @returns 保存结果
 */
export const databaseSave = async (form: RequestType): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: '/database/save',
    data: form,
    method: 'post'
  });
}
