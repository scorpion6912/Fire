/**
 * La classe incident représente un incident.
 * @class
 */
export default class Incident {
  /**
   * Crée une instance de Incident.
   * @constructor
   * @param {number} id
   * @param {string} type
   * @param {double} lon
   * @param {double} lat
   * @param {double} severity
   * @param {Date}   date_start
   * @param {Date}   date_end
   * @param {string} status
   */

  constructor(id, type, lon, lat, severity, dateStart, dateEnd, status) {
    this.id = id;
    this.type = type;
    this.lon = lon;
    this.lat = lat;
    this.severity = severity;
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
    this.status = status;
  }

  getId() {
    return this.id;
  }
  
  setId(id) {
    this.id = id;
  }
  
  getType() {
    return this.type;
  }
  
  setType(type) {
    this.type = type;
  }
  
  getLon() {
    return this.lon;
  }
  
  setLon(lon) {
    this.lon = lon;
  }
  
  getLat() {
    return this.lat;
  }
  
  setLat(lat) {
    this.lat = lat;
  }
  
  getSeverity() {
    return this.severity;
  }
  
  setSeverity(severity) {
    this.severity = severity;
  }

  getDateStart() {
  return this.dateStart;
  }

  setDateStart(dateStart) {
    this.dateStart = dateStart;
  }

  getDateEnd() {
    return this.dateEnd;
  }

  setDateEnd(dateEnd) {
    this.dateEnd = dateEnd;
  }

  getStatus() {
    return this.status;
  }

  setStatus(status) {
    this.status = status;
  }
}