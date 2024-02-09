const API_URL = "http://localhost:9080";

/**
 * Renvoie l'URI pour obtenir un utilisateur.
 * @returns {string} L'URI pour obtenir l'utilisateur.
 */
export function getSeverity(severity, heatwave) {
  const params = new URLSearchParams();
  params.append('severity', severity);
  params.append('heatwave', heatwave);
  return fetch(`${API_URL}/sensor/start?${params.toString()}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      // Add any other headers if needed
    },
    // You can add additional options here if needed
  })
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      // Handle the response data as needed
      return response.json();
    })
    .catch(error => {
      console.error('Error:', error);
      throw error; // You may want to handle the error more gracefully
    });
}
