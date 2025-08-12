import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

export default {
  getTasks() {
    return api.get('/tasks');
  },
  getTask(id) {
    return api.get(`/tasks/${id}`);
  },
  createTask(task) {
    return api.post('/tasks', task);
  },
  updateTask(id, task) {
    return api.put(`/tasks/${id}`, task);
  },
  deleteTask(id) {
    return api.delete(`/tasks/${id}`);
  }
};