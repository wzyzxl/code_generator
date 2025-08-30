/**
 * 请求类型
 */
export default interface RequestType {
  databaseId: number,
  databaseType: string,
  host: string,
  port: number,
  databaseName: string,
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
}
