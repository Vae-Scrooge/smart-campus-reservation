import request from './request'

export function createReservation(data) {
  return request.post('/reservations', data)
}

export function getMyReservations() {
  return request.get('/reservations')
}

export function cancelReservation(id) {
  return request.post(`/reservations/${id}/cancel`)
}

export function checkIn(id) {
  return request.post(`/reservations/${id}/checkin`)
}
