import type RequestType from "@/types/request_type";
import type ResponseType from "@/types/response_type";
import httpInstance from "@/utils/http";
import type { AxiosResponse } from "axios";

/**
 * 获取数据库类型
 * @param form 表单元素
 * @returns 数据库类型
 */
export const getFileNames = async (form: RequestType): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: '/code/filenames',
    method: 'post',
    data: form
  });
}

/**
 * 获取代码
 * @param form 表单元素
 * @returns 代码
 */
export const getCodeByFileName = async (form: RequestType): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: '/code/content',
    method: 'post',
    data: form
  });
}

/**
 * 下载zip文件
 * @param form 表单元素
 * @returns 下载信息
 */
export const downloadZipFile = async (form: RequestType): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: '/code/download',
    method: 'post',
    data: form
  });
}
