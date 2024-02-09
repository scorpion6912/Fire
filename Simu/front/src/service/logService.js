function prettyLog(what) {
  const now = new Date();
  const timestamp = now.toLocaleString();
  return `${timestamp} - ${what}`;
}

/**
Affiche une information dans la console.
@param {any} what - La valeur à afficher.
*/
export function logApp(what) {
  if (import.meta.env.DISPLAY_LOG === "yes" || import.meta.env.DISPLAY_LOG === undefined) {
    console.log(prettyLog(what));
  }
}

export function logAppWarn(what) {
  if (import.meta.env.DISPLAY_LOG === "yes" || import.meta.env.DISPLAY_LOG === undefined) {
    console.warn(prettyLog(what));
  }
}

export function logAppError(what) {
  if (import.meta.env.DISPLAY_LOG === "yes" || import.meta.env.DISPLAY_LOG === undefined) {
    console.error(prettyLog(what));
  }
}
