export function setStorage(key, value) {
  window.localStorage.setItem(key, JSON.stringify(value));
}

export function getStorage(key) {
  const data = window.localStorage.getItem(key);
  if (data === "" || data == null || data === "null") return null;
  try {
    return JSON.parse(data);
  } catch (err) {
    return data;
  }
}

export function removeStorage(key) {
  window.localStorage.removeItem(key);
}

export function clearStorage() {
  window.localStorage.clear();
}

export function setSessionStorage(key, value) {
  window.sessionStorage.setItem(key, JSON.stringify(value));
}

export function getSessionStorage(key) {
  const data = window.sessionStorage.getItem(key);
  if (data === "" || data == null || data === "null") return null;
  try {
    return JSON.parse(data);
  } catch (err) {
    return data;
  }
}

export function removeSessionStorage(key) {
  window.sessionStorage.removeItem(key);
}

export function formatNumber(num, digit = 2) {
  if (num === null || num === undefined || Number.isNaN(Number(num))) return "--";
  return Number(num).toFixed(digit);
}

export function debounce(fn, delay = 300) {
  let timer = null;
  return function (...args) {
    if (timer) clearTimeout(timer);
    timer = setTimeout(() => {
      fn.apply(this, args);
      timer = null;
    }, delay);
  };
}

export function throttle(fn, interval = 300) {
  let lastTime = 0;
  return function (...args) {
    const now = Date.now();
    if (now - lastTime >= interval) {
      fn.apply(this, args);
      lastTime = now;
    }
  };
}

export function deepClone(target) {
  if (typeof target !== "object" || target === null) return target;
  return JSON.parse(JSON.stringify(target));
}

export default {
  setStorage,
  getStorage,
  removeStorage,
  clearStorage,
  setSessionStorage,
  getSessionStorage,
  removeSessionStorage,
  formatNumber,
  debounce,
  throttle,
  deepClone
};
