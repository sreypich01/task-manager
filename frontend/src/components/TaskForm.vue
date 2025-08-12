<template>
  <form @submit.prevent="saveTask" class="mb-6 space-y-4">
    <!-- Title -->
    <div>
      <label class="block font-semibold mb-1">Title</label>
      <input
        v-model="task.title"
        type="text"
        placeholder="Enter title"
        class="w-full border border-gray-300 rounded p-2"
      />
      <p v-if="errors.title" class="text-red-500 text-sm mt-1">{{ errors.title }}</p>
    </div>

    <!-- Description -->
    <div>
      <label class="block font-semibold mb-1">Description</label>
      <textarea
        v-model="task.description"
        placeholder="Enter description"
        class="w-full border border-gray-300 rounded p-2"
      ></textarea>
      <p v-if="errors.description" class="text-red-500 text-sm mt-1">{{ errors.description }}</p>
    </div>

    <!-- Status -->
    <div>
      <label class="block font-semibold mb-1">Status</label>
      <select
        v-model="task.status"
        class="w-full border border-gray-300 rounded p-2"
      >
        <option disabled value="">Select status</option>
        <option value="PENDING">Pending</option>
        <option value="IN_PROGRESS">In Progress</option>
        <option value="COMPLETED">Completed</option>
      </select>
      <p v-if="errors.status" class="text-red-500 text-sm mt-1">{{ errors.status }}</p>
    </div>

    <!-- Buttons -->
    <div class="flex gap-2">
      <button
        type="submit"
        class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
      >
        {{ task.id ? 'Update Task' : 'Add Task' }}
      </button>
      <button
        v-if="task.id"
        type="button"
        @click="$emit('cancel')"
        class="bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-500"
      >
        Cancel
      </button>
    </div>

    <!-- General error message -->
    <p v-if="errors.general" class="text-red-500 mt-2">{{ errors.general }}</p>
  </form>
</template>

<script>
import api from '../api.js';

export default {
  props: ['selectedTaskId'],
  data() {
    return {
      task: { title: '', description: '', status: '' },
      errors: {}
    };
  },
  watch: {
    selectedTaskId: {
      immediate: true,
      async handler(newId) {
        this.errors = {};
        if (newId) {
          try {
            const res = await api.getTask(newId);
            this.task = res.data;
          } catch (err) {
            console.error('Failed to load task', err);
          }
        } else {
          this.task = { title: '', description: '', status: '' };
        }
      }
    }
  },
  methods: {
    async saveTask() {
      this.errors = {};

      // Client-side validation
      if (!this.task.title || this.task.title.trim().length < 3) {
        this.errors.title = 'Title must be at least 3 characters.';
      }
      if (!this.task.status) {
        this.errors.status = 'Please select a status.';
      }
      if (Object.keys(this.errors).length > 0) {
        return; // Stop submit if errors exist
      }

      try {
        if (this.task.id) {
          await api.updateTask(this.task.id, this.task);
        } else {
          await api.createTask(this.task);
        }
        this.$emit('saved');
        this.task = { title: '', description: '', status: '' };
      } catch (err) {
        if (err.response && err.response.status === 400 && err.response.data) {
          const backendErrors = {};
          if (Array.isArray(err.response.data.errors)) {
            err.response.data.errors.forEach(e => {
              backendErrors[e.field] = e.defaultMessage;
            });
          } else {
            backendErrors.general = 'Validation failed on server.';
          }
          this.errors = backendErrors;
        } else {
          this.errors.general = 'Failed to save task.';
        }
      }
    }
  }
};
</script>
