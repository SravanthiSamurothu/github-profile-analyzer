async function searchUser() {

    const username =
        document.getElementById("username").value;

    if (!username) {
        alert("Enter a GitHub username");
        return;
    }

    try {

        const profileResponse =
            await fetch(
                `/api/github/profile/${username}`
            );

        const profile =
            await profileResponse.json();

        const statsResponse =
            await fetch(
                `/api/github/stats/${username}`
            );

        const stats =
            await statsResponse.json();

        const reposResponse =
            await fetch(
                `/api/github/repositories/${username}`
            );

        const repos =
            await reposResponse.json();

        displayProfile(profile);
        displayStats(stats);
        displayRepositories(repos);

        drawLanguageChart(
            stats.languageDistribution
        );

    } catch (error) {

        alert("User not found");

        console.log(error);
    }
}

function displayProfile(profile) {

    document.getElementById("profile").innerHTML = `
    
    <div class="profile-card">

        <img src="${profile.avatar_url}"
             alt="Profile Image">

        <div class="profile-info">

            <h2>${profile.name || "No Name"}</h2>

            <p>
                <strong>Username:</strong>
                ${profile.login}
            </p>

            <p>
                <strong>Bio:</strong>
                ${profile.bio || "No Bio"}
            </p>

            <p>
                <strong>Location:</strong>
                ${profile.location || "N/A"}
            </p>

            <p>
                <strong>Followers:</strong>
                ${profile.followers}
            </p>

            <p>
                <strong>Following:</strong>
                ${profile.following}
            </p>

            <p>
                <strong>Public Repositories:</strong>
                ${profile.public_repos}
            </p>

            <p>
                <a href="${profile.html_url}" target="_blank">
                    View GitHub Profile ↗
                </a>
            </p>

        </div>

    </div>
    `;
}

function displayStats(stats) {

    document.getElementById("stats").innerHTML = `

    <div class="stats-container">

        <div class="stat-card">
            <h3>${stats.totalRepositories}</h3>
            <p>Total Repositories</p>
        </div>

        <div class="stat-card">
            <h3>${stats.totalStars}</h3>
            <p>Total Stars</p>
        </div>

        <div class="stat-card">
            <h3>${stats.totalForks}</h3>
            <p>Total Forks</p>
        </div>

        <div class="stat-card">
            <h3>${stats.mostUsedLanguage}</h3>
            <p>Most Used Language</p>
        </div>

    </div>
    `;
}

function displayRepositories(repositories) {

    let html = "<h2>Repositories</h2>";

    repositories.forEach(repo => {

        html += `

        <div class="repo-card">

            <h3>${repo.name}</h3>

            <p>
                ${repo.description ? repo.description : "No Description Available"}
            </p>

            <p>
                <strong>Language:</strong>
                ${repo.language}
            </p>

            <p>
                ⭐ ${repo.stars}
                | Forks: ${repo.forks}
            </p>

        </div>
        `;
    });

    document.getElementById(
        "repositories"
    ).innerHTML = html;
}

let chart;

function drawLanguageChart(languageDistribution){

    const labels =
        Object.keys(languageDistribution);

    const values =
        Object.values(languageDistribution);

    const ctx =
        document.getElementById("languageChart");

    if(chart){
        chart.destroy();
    }

    chart = new Chart(ctx,{

        type:"pie",

        data:{
            labels:labels,

            datasets:[{
                data:values
            }]
        },

        options:{
            responsive:true,

            plugins:{
                legend:{
                    labels:{
                        color:"white"
                    }
                }
            }
        }
    });
}