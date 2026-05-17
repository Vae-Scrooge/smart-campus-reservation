import request from './request'

export function getAdminResources() {
  return request.get('/admin/resources')
}

export function createResource(data) {
  return request.post('/admin/resources', data)
}

export function getAllReservations() {
  return request.get('/admin/reservations')
}
