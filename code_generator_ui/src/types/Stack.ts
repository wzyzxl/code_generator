/**
 * 栈数据结构
 */
export class Stack<T> {
  // 用数组存储栈元素
  private items: T[];

  // 初始化栈
  constructor() {
    this.items = [];
  }

  /**
   * 入栈操作：向栈顶添加元素
   * @param element 要添加的元素
   */
  push(element: T): void {
    this.items.push(element);
  }

  /**
   * 出栈操作：移除并返回栈顶元素
   * @returns 栈顶元素，如果栈为空则返回null
   */
  pop(): T | null {
    if (this.isEmpty()) {
      return null;
    }
    return this.items.pop() as T;
  }

  /**
   * 查看栈顶元素（不移除）
   * @returns 栈顶元素，如果栈为空则返回null
   */
  peek(): T | null {
    if (this.isEmpty()) {
      console.warn("栈为空，无栈顶元素");
      return null;
    }
    return this.items[this.items.length - 1];
  }

  /**
   * 判断栈是否为空
   * @returns 如果栈为空则返回true，否则返回false
   */
  isEmpty(): boolean {
    return this.items.length === 0;
  }

  /**
   * 获取栈的大小（元素个数）
   * @returns 栈中元素的数量
   */
  size(): number {
    return this.items.length;
  }

  /**
   * 清空栈
   */
  clear(): void {
    this.items = [];
  }

  /**
   * 获取栈的所有元素（返回副本，避免外部修改内部数组）
   * @returns 栈元素数组（从栈底到栈顶）
   */
  toArray(): T[] {
    return [...this.items];
  }
}
