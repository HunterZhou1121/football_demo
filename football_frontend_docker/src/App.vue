<script setup>
import { ref } from 'vue'

// Define reactive variable to store players data
const players = ref([])

// Function to fetch players data
const fetchPlayers = async () => {
  try {
    const response = await fetch('http://localhost:8080/players')
    players.value = await response.json()
  } catch (error) {
    console.error('Error fetching players:', error)
  }
}
// Note: not football_backend:8080, because the JavaScript code is running in the browser ON THE HOST MACHINE
</script>

<template>
  <main>
    <h1>Football Players Database</h1>
    
    <button @click="fetchPlayers" class="query-btn">Query</button>

    <div class="table-container" v-if="players.length">
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Age</th>
            <th>Club</th>
            <th>Country</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="player in players" :key="player.playerName">
            <td>{{ player.playerName }}</td>
            <td>{{ player.playerAge }}</td>
            <td>{{ player.playerClub }}</td>
            <td>{{ player.playerCountry }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </main>
</template>

<style scoped>
main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.query-btn {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 20px;
}

.query-btn:hover {
  background-color: #45a049;
}

.table-container {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #ddd;
  padding: 12px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

tr:hover {
  background-color: #f5f5f5;
}
</style>
