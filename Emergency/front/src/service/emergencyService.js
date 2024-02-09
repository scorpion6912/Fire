import { getONGOINGIncidentUri, getManagementIncidentUri } from "./emergencyUriService"
import { logApp } from "./logService"

/**
 * Retrieves all ONEGOING incidents from the API.
 *
 * @async
 * @returns {Promise<Object>} - A promise that resolves to the JSON response of the API call.
 */
export async function getONGOINGIncident() {
    const url = getONGOINGIncidentUri();
    logApp(`Request to ${url}`);
    return (await fetch(url)).json();
  }

/**
 * Retrieves all managementIncidents from the API.
 *
 * @async
 * @returns {Promise<Object>} - A promise that resolves to the JSON response of the API call.
 */
export async function getManagementIncident() {
    const url = getManagementIncidentUri();
    logApp(`Request to ${url}`);
    return (await fetch(url)).json();
  }