const api_url = "https://tash.wtf/api";

async function getTripsApi(by, order) {
  try {
    const response = await fetch(
      `${api_url}/trips?sortBy=${by}&order=${order}`
    );
    const data = await response.json();
    return data;
  } catch (error) {
    return error;
  }
}

async function getTripDetailApi(id) {
  try {
    const response = await fetch(`${api_url}/trips/${id}`);
    const data = await response.json();
    return data;
  } catch (error) {
    return error;
  }
}

async function getAllTags() {
  const res = await fetch(`${api_url}/tags`);
  const data = await res.json();
  return data.map((e) => ({
    title: e.name,
  }));
}

async function getAllCountries() {
  const res = await fetch(`${api_url}/countries`);
  const data = await res.json();
  return data.map((e) => ({
    title: e.name,
  }));
}

async function getTrips(tags, isAllTags, countries, isAllCountries, limit) {
  let res = await fetch(
    api_url +
      "/trips?tag=" +
      tags.join(",") +
      "&tagAny=" +
      isAllTags +
      "&country=" +
      countries.join(",") +
      "&countryAny=" +
      isAllCountries
  );
  res = await res.json();
  return res;
}

const API = {
  getTripDetailApi,
  getTripsApi,
  getAllTags,
  getAllCountries,
  getTrips,
};
export default API;
