// Function to handle API errors
function handleApiError(error) {
    console.error('API Error:', error);
    if (error.status === 401) {
        window.location.href = '/login';
    }
}

// Fetch and display dashboard stats
async function fetchDashboardStats() {
    try {
        const response = await fetch('/api/activities');
        if (!response.ok) {
            throw response;
        }
        const activities = await response.json();

        // Calculate totals
        const totals = activities.reduce((acc, activity) => {
            return {
                calories: acc.calories + activity.calories,
                distance: acc.distance + activity.distance,
                duration: acc.duration + activity.duration
            };
        }, { calories: 0, distance: 0, duration: 0 });

        // Update the dashboard
        document.getElementById('totalCalories').textContent = `${totals.calories} kcal`;
        document.getElementById('totalDistance').textContent = `${totals.distance.toFixed(2)} km`;
        document.getElementById('totalTime').textContent = `${totals.duration} mins`;
    } catch (error) {
        handleApiError(error);
    }
}

// Load past activities
async function loadPastActivities() {
    try {
        const response = await fetch('/api/activities');
        if (!response.ok) {
            throw response;
        }
        const data = await response.json();
        const activityList = document.getElementById('activityList');
        activityList.innerHTML = '';
        if (data.length === 0) {
            activityList.innerHTML = '<li>No activities recorded yet</li>';
            return;
        }
        data.forEach((activity) => {
            const li = document.createElement('li');
            li.textContent = `${activity.activityType} - ${activity.date} - ${activity.calories} kcal - ${activity.distance} km - ${activity.duration} minutes`;
            activityList.appendChild(li);
        });
    } catch (error) {
        handleApiError(error);
    }
}

// Initialize form submission handler
function initializeFormHandler() {
    const activityForm = document.querySelector('#activities form');
    if (activityForm) {
        activityForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            const workout = {
                activityType: document.getElementById('activityType').value,
                calories: parseInt(document.getElementById('calories').value),
                distance: parseFloat(document.getElementById('distance').value),
                duration: parseInt(document.getElementById('duration').value),
                date: document.getElementById('date').value,
            };

            try {
                const response = await fetch('/api/logActivity', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(workout),
                });

                if (!response.ok) {
                    throw response;
                }

                const data = await response.json();
                if (data) {
                    alert('Activity logged successfully!');
                    this.reset();
                    await loadPastActivities();
                    await fetchDashboardStats();
                }
            } catch (error) {
                handleApiError(error);
                alert('Failed to log activity. Please try again.');
            }
        });
    }
}

// Handle logout
function initializeLogoutHandler() {
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(e) {
            // The form will handle the actual logout
            // No need to prevent default or redirect manually
        });
    }
}

// Initialize the page
window.addEventListener('DOMContentLoaded', async function() {
    try {
        // Initialize form and logout handlers
        initializeFormHandler();
        initializeLogoutHandler();

        // Load initial data
        await Promise.all([
            loadPastActivities(),
            fetchDashboardStats()
        ]);
    } catch (error) {
        handleApiError(error);
    }
});