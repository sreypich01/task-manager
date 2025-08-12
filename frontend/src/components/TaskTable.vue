<template>
  <table class="w-full border border-gray-300 rounded overflow-hidden">
    <thead>
      <tr class="bg-gray-200">
        <th class="p-2 text-left">ID</th>
        <th class="p-2 text-left">Title</th>
        <th class="p-2 text-center">Actions</th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="task in tasks"
        :key="task.id"
        class="border-t border-gray-300"
      >
        <td class="p-2">{{ task.id }}</td>
        <td class="p-2">{{ task.title }}</td>
        <td class="p-2 text-center">
          <button
            @click="$emit('edit', task)"
            class="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600"
          >
            Edit
          </button>
          <button
            @click="$emit('requestDelete', task.id)"
            class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 ml-2"
          >
            Delete
          </button>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  props: ['tasks']
};
</script>
