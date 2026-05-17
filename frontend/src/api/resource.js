import request from './request'

export function getResources() {
  return request.get('/resources')
}

export function getResourcesByType(type) {
  return request.get(`/resources/type/${type}`)
}

export function getResource(id) {
  return request.get(`/resources/${id}`)
}
