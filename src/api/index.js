const api_url = "https://tash.wtf/api";

export async function getTripsApi(by, order) {
  try {
    const response = await fetch(`${api_url}/trips?sortBy=${by}&order=${order}`);
    const data = await response.json();
    return data;
  } catch (error) {
    return error;
  }
}

export async function getTripDetailApi(id) {
  try {
    const response = await fetch(`${api_url}/trips/${id}`);
    const data = await response.json();
    return data;
  } catch (error) {
    return error;
  }
}
