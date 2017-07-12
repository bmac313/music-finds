function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    var greetingText = document.getElementById('greeting-text');
    var textNode = document.createTextNode('Welcome, ' + profile.getName());

    greetingText.innerText = textNode.textContent;
    console.log('Signed in as ' + profile.getName());
}

function signOutUser() {
    var auth2 = gapi.auth2.getAuthInstance();

    auth2.signOut().then(function() {
        var greetingText = document.getElementById('greeting-text');
        var textNode = document.createTextNode('');
        greetingText.innerText = textNode.textContent;
        console.log('Signed out of MusicFinds');
    });
}