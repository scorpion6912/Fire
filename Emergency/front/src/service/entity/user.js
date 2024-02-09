/**
 * La classe user représente une personne.
 * @class
 */
export class User {
  /**
   * Crée une instance de User.
   * @constructor
   * @param {number} id - l'id de l'utilisateur.
   */
  constructor(id) {
    this.id = id;
  }

  getId() {
    return this.id;
  }
}