const API_URL = "http://localhost:8080";

/**
 * Renvoie l'URI pour obtenir tout les incidents ONGOING.
 * @returns {string} L'URI pour obtenir les incidents ONGOING.
 */
export function getONGOINGIncidentUri() {
  return `${API_URL}/incident/ongoing`;
}

/**
 * Renvoie l'URI pour obtenir tout les managementIncidents.
 * @returns {string} L'URI pour obtenir les managementIncidents.
 */
export function getManagementIncidentUri() {
  return `${API_URL}/managementIncident`;
}
