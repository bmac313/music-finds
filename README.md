# MusicFinds

====

Description:
* MusicFinds is a web app that lets users can share their finds at record stores (including vinyl, CDs, and others). It's intended to be used to share items you aren't buying, but think someone else might be interested in.
* To see how the app works, visit <a href="http://music-finds.herokuapp.com">http://music-finds.herokuapp.com</a>.

Important Notes:
* <strong>There is currently a bug</strong> where visiting any page directly before the site creates a user cookie causes an exception.
This cookie is created when you visit the site's root, so <strong>make sure to do this</strong> if you want to see how this site works.
Fortunately, I am working on updating the login system to use the Twitter API instead; once that is implemented, this
problem should be a thing of the past.