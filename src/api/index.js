const api_url = "http://localhost:8080";

export async function getTripsApi(by, order) {
  try {
    const response = await fetch(`${api_url}/trips/sort?by=${by}&order=${order}`);
    const data = await response.json();
    return data;
  } catch (error) {
    return error;
  }
}
