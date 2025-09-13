/**
 * 请求类型
 */
export default interface RequestType {
  databaseId: string | null,
  databaseType: string,
  host: string,
  port: number,
  databaseName: string,
  username: string,
  password: string,
  changeFlag: boolean,
  pattern: string,
  table: string,
  configName: string,
  createEntity: boolean,
  createRepository: boolean,
  createController: boolean,
  createService: boolean,
  createSimpleSql: boolean,
  prePackageName: string
  fileName: string
}
