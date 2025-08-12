<template>
  <div class="min-h-screen bg-gray-100 p-8">
    <div class="max-w-5xl mx-auto bg-white rounded-lg shadow-lg p-6">

      <h1 class="text-2xl font-bold mb-6">Task Manager</h1>

      <!-- Add Task button -->
      <button
        @click="openForm()"
        class="mb-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
      >
        Add Task
      </button>

      <!-- Modal wrapper for Task Form -->
      <div
        v-if="showForm"
        class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      >
        <div class="bg-white rounded-lg shadow-lg w-full max-w-lg p-6 relative">
          <button
            @click="closeForm"
            class="absolute top-3 right-3 text-gray-500 hover:text-gray-700 text-xl font-bold"
          >
            &times;
          </button>
          <TaskForm
            :selectedTaskId="selectedTaskId"
            @saved="onFormSaved"
            @cancel="closeForm"
          />
        </div>
      </div>

      <!-- Confirmation Modal for Delete -->
      <div
        v-if="showDeleteConfirm"
        class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      >
        <div class="bg-white rounded-lg shadow-lg max-w-sm p-6 text-center">
          <p class="mb-4 text-lg font-semibold">Are you sure you want to delete this task?</p>
          <div class="flex justify-center gap-4">
            <button
              @click="cancelDelete"
              class="px-4 py-2 rounded border border-gray-400 hover:bg-gray-100"
            >
              Cancel
            </button>
            <button
              @click="confirmDelete"
              class="px-4 py-2 rounded bg-red-600 text-white hover:bg-red-700"
            >
              Delete
            </button>
          </div>
        </div>
      </div>

      <!-- Task Table -->
      <TaskTable
        :tasks="tasks"
        @edit="openForm"
        @requestDelete="promptDelete"
      />

      <p v-if="error" class="text-red-500 mt-4">{{ error }}</p>
    </div>
  </div>
</template>

<script>
import TaskForm from './components/TaskForm.vue';
import TaskTable from './components/TaskTable.vue';
import api from './api.js';

export default {
  components: { TaskForm, TaskTable },
  data() {
    return {
      tasks: [],
      selectedTaskId: null,
      showForm: false,
      error: '',
      showDeleteConfirm: false,
      taskToDeleteId: null
    };
  },
  methods: {
    async fetchTasks() {
      try {
        const res = await api.getTasks();
        this.tasks = res.data;
        this.error = '';
      } catch (err) {
        this.error = 'Failed to fetch tasks.';
      }
    },
    openForm(task = null) {
      this.selectedTaskId = task ? task.id : null;
      this.showForm = true;
    },
    closeForm() {
      this.showForm = false;
      this.selectedTaskId = null;
    },
    onFormSaved() {
      this.fetchTasks();
      this.closeForm();
    },
    promptDelete(taskId) {
      this.taskToDeleteId = taskId;
      this.showDeleteConfirm = true;
    },
    cancelDelete() {
      this.taskToDeleteId = null;
      this.showDeleteConfirm = false;
    },
    async confirmDelete() {
      try {
        await api.deleteTask(this.taskToDeleteId);
        this.fetchTasks();
        this.cancelDelete();
      } catch (err) {
        this.error = 'Failed to delete task.';
        this.cancelDelete();
      }
    }
  },
  mounted() {
    this.fetchTasks();
  }
};
</script>
