import { ElLoading, ElMessage } from "element-plus";

/**
 * 防抖处理函数
 * @param func 要执行的函数
 * @param delay 延迟时间(ms)
 * @param params 处理后的函数
 * @returns 执行函数
 */
export const debounce = (func: Function, delay: number, ...params: any) => {
  let timerId: number;
  return () => {
    clearTimeout(timerId);
    timerId = setTimeout(() => {
      func(...params);
    }, delay);
  }
}

/**
 * 加载组件
 */
let loadingInstance: any = null
let loadingCount = 0
export const loadingComponent = {
  // 全屏加载
  fullLoading: (start: boolean) => {
    if (start) {
      loadingCount++
      if (loadingCount === 1) {
        // 第一次请求，打开 loading
        loadingInstance = ElLoading.service({
          lock: true,
          text: '加载中...',
          background: 'rgba(0, 0, 0, 0.7)'
        })
      }
    } else {
      loadingCount--
      if (loadingCount <= 0) {
        loadingCount = 0
        loadingInstance?.close()
        loadingInstance = null
      }
    }
  }
}
/**
 * 复制文字到剪切板
 * @param text 待复制文本
 */
export const copyTextToClipboard = async (text: string) => {
  // 复制代码
  try {
    // 使用现代浏览器的 Clipboard API
    await navigator.clipboard.writeText(text);
    ElMessage.success("复制成功");
  } catch (err) {
    // 兼容旧浏览器的复制方法
    const textarea = document.createElement('textarea');
    textarea.value = text;
    textarea.style.position = 'fixed';
    textarea.style.opacity = '0';
    document.body.appendChild(textarea);
    textarea.select();

    try {
      document.execCommand('copy');
      ElMessage.success('复制成功');
    } catch (err) {
      ElMessage.error('复制失败，请手动复制');
    }

    document.body.removeChild(textarea);
  }
}

/**
 * 根据文件链接触发下载
 * @param {string} url - 文件下载链接
 * @param {string} [fileName] - 可选
 */
export const downloadFile = (url: string, fileName: string = "代码.zip") => {
  // 创建a标签
  const link = document.createElement('a');
  // 设置下载链接
  link.href = url;
  // 指定文件名
  if (fileName) {
    link.download = fileName;
  }
  // 隐藏a标签
  link.style.display = 'none';
  // 添加到文档
  document.body.appendChild(link);
  // 模拟点击
  link.click();
  // 移除a标签（清理DOM）
  document.body.removeChild(link);
};
