import type ResponseType from "@/types/response_type";
import httpInstance from "@/utils/http";
import type { AxiosResponse } from "axios";

/**
 * 查询文件列表
 * @returns 文件列表
 */
export const queryFiles = async (path: string): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: '/files',
    method: 'get',
    params: {
      path: path
    }
  });
}

/**
 * 删除文件
 * @param type 文件类型
 * @param path 文件路径
 * @returns 删除状态
 */
export const deleteFiles = async (type: string, path: string): Promise<AxiosResponse<ResponseType>> => {
  return httpInstance({
    url: '/files/delete',
    method: 'delete',
    data: {
      type: type,
      path: path
    }
  });
}
