const APP_NAME_IN_CACHE = "emergencies";
/**
 * Une fonction pour récupérer de la données depuis le cache pour les appels API,
 * @param {*} url L'URL à partir de laquelle on souhaite récupérer les données
 * @param {*} dataType Une chaîne de caractère pour distinguer les différents types de données que l'on récupére
 * @param {*} cacheVersion version cachée. Sert notamment pour
 * @returns
 */
export async function getData(url, dataType, cacheVersion) {
  const cacheName = getCacheName(dataType, cacheVersion);
  let cachedData = await getCachedData(cacheName, url);

  if (cachedData) {
    return cachedData;
  }

  const cacheStorage = await caches.open(cacheName);
  await cacheStorage.add(url);
  cachedData = await getCachedData(cacheName, url);
  await deleteOldCaches(cacheName, dataType);
  return cachedData;
}

// Get data from the cache.
async function getCachedData(cacheName, url) {
  const cacheStorage = await caches.open(cacheName);
  const cachedResponse = await cacheStorage.match(url);

  if (!cachedResponse || !cachedResponse.ok) {
    return undefined;
  }

  return await cachedResponse.json();
}

async function deleteOldCaches(currentCache, dataType) {
  const keys = await caches.keys();

  for (const key of keys) {
    const isOurCache = key.startsWith(`${APP_NAME_IN_CACHE}-${dataType}`);
    if (currentCache !== key && isOurCache) {
      caches.delete(key);
    }
  }
}

function getCacheName(dataType, cacheVersion) {
  return `${APP_NAME_IN_CACHE}-${dataType}-${cacheVersion}`;
}
