const api_url = "";

export async function getTripsApi(by, order) {
  try {
    const response = await fetch(`${api_url}/api/trips/sort?by=${by}&order=${order}`);
    const data = await response.json();
    return data;
  } catch (error) {
    return error;
  }
}
