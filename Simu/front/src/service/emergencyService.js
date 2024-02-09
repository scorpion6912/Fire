import { logApp } from "./logService"

/**
 * Retrieves a sensor from the API.
 *
 * @async
 * @returns {Promise<Object>} - A promise that resolves to the JSON response of the API call.
 */

export async function getSensor() {
      const url = "http://localhost:9080/sensor";
      logApp(`Request to ${url}`);
      return (await fetch(url)).json();
}
