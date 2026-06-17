export function getCurDate() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}

export function getCurTime() {
  const now = new Date();
  const hours = String(now.getHours()).padStart(2, "0");
  const minutes = String(now.getMinutes()).padStart(2, "0");
  const seconds = String(now.getSeconds()).padStart(2, "0");
  return `${hours}:${minutes}:${seconds}`;
}

export function setSessionStorage(keyStr, value) {
  sessionStorage.setItem(keyStr, JSON.stringify(value));
}

export function getSessionStorage(keyStr) {
  const str = sessionStorage.getItem(keyStr);
  if (str === "" || str == null || str === "null") {
    return null;
  }
  return JSON.parse(str);
}

export function removeSessionStorage(keyStr) {
  sessionStorage.removeItem(keyStr);
}

export function setLocalStorage(keyStr, value) {
  localStorage.setItem(keyStr, JSON.stringify(value));
}

export function getLocalStorage(keyStr) {
  const str = localStorage.getItem(keyStr);
  if (str === "" || str == null || str === "null") {
    return null;
  }
  return JSON.parse(str);
}

export function removeLocalStorage(keyStr) {
  localStorage.removeItem(keyStr);
}
