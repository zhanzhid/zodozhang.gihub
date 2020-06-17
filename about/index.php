<!doctype html>
<!--[if lt IE 7 ]> <html class="ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8" lang="en"> <![endif]-->
<!--[if IE 9 ]>    <html class="ie9" lang="en"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <!--<![endif]-->
<html lang="en">
    <?php /*variables*/
    $sitename="R&eacute;sum&eacute;";     
    $slogan="Student of Computer Science in Oregon State University";
    $sitepath="http://people.oregonstate.edu/~zhanzhid/about";    
    $author="Zhidong Zhang";
    ?>
 <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
     <meta charset="utf-8" />
   <title><?php echo $author.", ".$sitename; ?></title>
     <meta name = "viewport" content="width=device-width,initial-scale=1" />
     <meta name="description" content="<?php echo $slogan; ?>" />
     <meta name="author" content="<?php echo $author; ?>" />
     <meta name="keywords" content="zhidong zhang resume, Oregonstate.edu, student of computer science at OSU, born in 1996. " />
     <meta name="robots" content="noindex,nofollow,noarchive" />
    <!--  links -->
    <link href="https://necolas.github.io/normalize.css/3.0.3/normalize.css" type="text/css" rel="stylesheet" />
    <link href="main.css" rel="stylesheet"  />
    <link href = "slideshow.css" rel = "stylesheet" />
    <!-- fonts-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
   <link href="https://fonts.googleapis.com/css?family=Anton%7COpen+Sans" rel="stylesheet">    
   <link rel="stylesheet" href="https://s3.amazonaws.com/icomoon.io/114779/Socicon/style.css?u8vidh">
    <!-- favicon -->
   <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
<link rel="manifest" href="/site.webmanifest">
<link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5">
<meta name="msapplication-TileColor" content="#00aba9">
<meta name="theme-color" content="#ffffff">
    <script src="modernizr-dev-min.js"></script>

</head>
<body>
  <header>
    <a href="http://people.oregonstate.edu/~zhanzhid/about">
      <img class="move dropshadow" src="img/zhidong.png"
        alt="<?php echo $sitename." for ".$author; ?>"
        title="<?php echo $slogan; ?>"
        id="firstname"
        /><img class="move dropshadow" src="img/symbol.png"
        alt="<?php echo $sitename." for ".$author; ?>"
        title="<?php echo $slogan; ?>"
        id="symbol"
        /><img class="move dropshadow" src="img/zhang.png"
        alt="<?php echo $sitename." for ".$author; ?>g"
        title="<?php echo $slogan; ?>"
        id="lastname"
        /><img src="img/zhidong-zhang-symbol.png"
        alt="<?php echo $sitename." for ".$author; ?>"
        title="<?php echo $slogan; ?>"
        id="combo"
        class="drop-shadow"
        />
    </a>
    <p class="amber tshadow move"><?php echo $slogan; ?></p>
  </header>
<nav id="articles">
      <ul class="aligncenter">
         <li><a href="#education" onmousedown="sound.play()" class="sangria navyblue-bg scroll radius boxshadow" data-speed="1000"><i class="material-icons md-24 move">school</i>Education</a></li>
         <li><a href="#work" onmousedown="sound.play()"  class="amber cyan-bg scroll radius boxshadow" data-speed="1000"><i class="material-icons md-24 move">work</i>Work Experience</a></li>
         <li><a href="#service" onmousedown="sound.play()"  class="sangria peach-bg scroll radius boxshadow" data-speed="1000"><i class="material-icons md-24 move">room_service</i>Service</a></li>
         <li><a href="#skills" onmousedown="sound.play()" class="cyan amber-bg scroll radius boxshadow" data-speed="1000"><i class="material-icons md-24 move">build</i>Skills</a></li>
         <li><a href="#fun" onmousedown="sound.play()" class="peach sangria-bg scroll radius boxshadow" data-speed="1000"><i class="material-icons md-24 move">toys</i>Fun</a></li>
         <li><i class="material-icons" onclick="window.print(); return false" title="Opens the print dialog box.">print</i></li>
      </ul>
  </nav>
     <main id="top">
  <article id="about" class="peach-bg sangria radius boxshadow">
       <h1>About me</h1>
      <aside class="sangria-bg amber alignright boxshadow fifty">
     <figure class="alignleft thirty" >             
        <img class ="shape" src="img/zhanzhid-2.jpg" 
        alt="<?php echo $author; ?> self portrait" 
         title="&copy; <?php echo $author; ?> 2018" 
         srcset="img/zhanzhid-2.jpg 320w, img/zhanzhid-1.jpg 640w, img/zhanzhid-0.jpg 1080w"
        sizes="(max-width: 480px) 100vw, (max-width: 900px) 33vw, 254px"
    />
    <figcaption><?php echo $author; ?> self portrait</figcaption>
      </figure>       
        <p>i am OSU student, i like playing game, such as LOL, PUBG. Also i like playiing table tennis too.</p>          
        <!--audio about author or music selection -->
     </aside>     
     <h2>Contact</h2>             
     <address>
       <p> <a href="<?php echo $sitepath; ?>"><?php echo $author; ?></a>
       </p><p>1265 sw maple tree Ct.</p>
       <p>Corvallis, OR 97333</p>
        <p>Contact me using the form below. &darr;</p>
       <p><a href="#contact">Contact me using the form below. &darr;</a></p>
    </address>
    <br class="clear" />
      
  </article>
  <article id="education" class="peach sangria-bg radius boxshadow">
    <h2>Education</h2>
    <dl class="alignright fifty">
        <dt><a href="http://oregonstate.edu" target="_blank">Oregons State University</a></dt>
        <dd>Degree: Computer Science</dd>
        <dt>Advantage: like reserach</dt>
        <dd>Degree finished: 2020</dd>
    </dl>
                <figure class="slidebox">
                <img src="photos/zodo-code-in-library.jpg" alt="zhidong zhang coding in osu library" title="&copy; zhidong zhang 2018" />
                <img src="photos/zodo-listen-cs162-class.jpg" alt="zhidong zhang listening CS162 class" title="&copy; zhidong zhang 2018" />
                <img src="photos/myteacher-talking-in-class.jpg" alt="zhidong zhang listening CS195 class" title="&copy; zhidong zhang 2018" />
                <img src="photos/teacher-talking.jpg" alt="teacher is teaching in CS195" title="&copy; zhidong zhang 2018" />                
                <figcaption>Studying at OSU</figcaption>
            </figure>
            <br class="clear" />
</article>
  <article id="work" class="amber navyblue-bg boxshadow">
    <h2>work</h2>
    <dl>
        <dt>Experience</dt>
        <dd>I am a student right now, And i didn't take any job in school.</dd>
    </dl>
</article>
  <article id="service" class="black cyan-bg boxshadow">
    <h2>service</h2>
        <ul class="aligncenter">
        <li>I don't have any experiences T T.</li>
        </ul>
        <figure class="print-message navyblue cyan-bg">
         <iframe src="https://www.youtube.com/embed/T69iA024YGg" ></iframe>
          <figcaption>Happy New Year In LA <cite>&copy; 2018 Zhidong Zhang</cite>. 0:05. MP4.</figcaption>
        </figure>
</article>
 <article id="skills" class="navyblue amber-bg boxshadow">
    <h2>skills</h2>
   <table id="sort" class="sort">
     <caption>Programming Skills</caption>
     <thead>
    <tr>
        <th>Skill</th>
        <th>Beginner</th>
        <th>Intermediate</th>
        <th>Untile now</th> 
    </tr>
 </thead>
     <tbody>
    <tr>
        <th>C++ code</th>
        <td colspan="1"> 1 year of experience</td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <th>Reading novel</th>
        <td colspan="3"> 5 years of experience</td>
        <td></td>
    </tr>
    <tr>
        <th>play table tennis</th>
        <td colspan="2"> 3 years of experience</td>
        <td></td>
     </tr>
    </tbody>
    <tfoot>
    <tr>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
    </tr>
    </tfoot>
   </table>
</article>
<article id="fun" class="masonry cyan-bg boxshadow">
    <h2>fun</h2>
<img src="photos/pv1-cat-looking.jpg" 
     alt="funny cat looking at me" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
  <img src="photos/pv1-chinesefood-fish-eating.jpg" 
     alt="delicious chinese food" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
  <img src="photos/pv1-dog-yawning.jpg" 
     alt="This is my dog" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
  <img src="photos/pv1-friends-eatting.jpg" 
     alt="eating seafood with my firends in Los Angeles" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
  <img src="photos/pv1-friends-siting.jpg" 
     alt="taking photo with my firends in San Francisco" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
  <img src="photos/pv1-lover-photo-taking.jpg" 
     alt="This is my girlfriends" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
  <img src="photos/pv1-lovers-siting.jpg" 
     alt="taking photo with my beatiful girlfriend in Potato Chip Rock" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
  <img src="photos/pv1-Monalisa-hellokitty-smile.jpg" 
     alt="My grilfriend's drawing" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
  <img src="photos/pv1-sunsine-sky-lignting.jpg" 
     alt="sunset" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
  <img src="photos/pv1-tree-sky-standing.jpg" 
     alt="beatiful scenery in San Diego" 
     title="&copy; zhidong zhang 2017"
     class="caption" 
/>
</article>
  </main>
<footer class="peach sangria-bg radius boxshadow">   

<!-- contact form -->
   <?php
    /*variables*/
    $name = $_POST['firstlastname'];
    $email = $_POST['emailaddress'];
    $phone = $_POST['phone'];
    $web = $_POST['web'];
    $message = $_POST['message'];
    $from = $_POST['firstlastname']; 
    $to = 'ONID-USER-NAME@oregonstate.edu'; 
    $subject = '[Resume;] inquiry';
    $human = $_POST['human'];
    /*headers*/
    $headers .= "Reply-To: <{$_POST['emailaddress']}>";
    /*body of the email message*/
    $body = "$name $email from $web would like a call back at $phone. 

    They sent this message: $message\n";
        /*error messages*/
    if ($_POST['submit']) {
    if ($name != '' && $email != '') {
        if ($human == '4') {                 
            if (mail ($to, $subject, $body, $from)) { 
                echo '<p class="confirmation">Your message has been sent!</p>';
            } else { 
                echo '<p class="tryagain">Something went wrong. Please try again.</p>'; 
            } 
        } else if ($_POST['submit'] && $human != '4') {
            echo '<p class="tryagain">The form was cleared because you provided an incorrect Anti-Spam answer. Please try again. </p>';
        }
        } else {
            echo '<p class="tryagain">Please fill in all required fields.</p>';
        }
    }
    ?>  
  
  
  
<form id="contact" action="index.php" method="post">
 <h2>Contact me via this secure form.</h2>
      <fieldset class="boxshadow alignleft thirty radius gradient">
    <legend>Contact Information</legend>
    <label for="firstlastname" class="required">First and Last Name</label>
    <input name="firstlastname" id="firstlastname" type="text" required placeholder="Jane Doe"  maxlength="32"  />
    <label for="emailaddress" class="required">Email</label>
    <input name="emailaddress" id="emailaddress" type="email" required placeholder="Jane.Doe@email.com"  maxlength="32"  />
    <label for="phone">Phone</label>
    <input name="phone" id="phone" type="tel" required placeholder="000-000-0000"  maxlength="10"  />
    <label for="web">URL</label>
    <input name="web" id="web" type="url" required placeholder="http://oregonstate.edu"  maxlength="40"  />
    </fieldset>
        <fieldset class="boxshadow radius gradient">
    <legend>Please leave your message here</legend>
    <label for="subject">Select a
    <select name="subject" id="subject" required="" >
      <option value="">subject:</option>
      <option value="Employment">Employment</option>
      <option value="Contract">Contract</option>
      <option value="Personal">Personal</option>
    </select>
    </label>
    <label for="message">Type up to 480 characters.</label>
    <textarea id="message" name="message" required placeholder="Text only" maxlength="480" ></textarea>
    </fieldset>
    <fieldset class="boxshadow radius gradient">
    <legend>Submit Form</legend>
    <p>To help prevent spam, answer this mathematical equation:</p>
    <label for="human">What is 2 * 5 ?
    <input name="human" id="human" required maxlength="1" />
    </label>
    <label for="submit"></label>
    <input name="submit" id="submit" type="submit" value="Send" />
    <label for="reset"></label>
    <input name="reset" id="reset" type="reset" value="Reset" />
    </fieldset>
</form>
<!-- social media icons a img -->
<nav id="share">
  <i class="material-icons">share</i>

  <a href="http://twitter.com/share?url=http://oregonstate.edu/~zhanzhid/about/&text=Zhidong Zhang&hashtags=resume" target="_blank" class="circle boxshadow">
    <i class="socicon-twitter"></i>
  </a>
  
  <a href="http://www.facebook.com/sharer.php?u=http://oregonstate.edu/~zhanzhid/about/" target="_blank" class="circle boxshadow">
    <i class="socicon-facebook"></i>
  </a>
  
  <a href="https://plus.google.com/share?url=http://oregonstate.edu/~zhanzhid/about/" target="_blank" class="circle boxshadow">
    <i class="socicon-googleplus"></i>
  </a>
  
  <a href="http://www.linkedin.com/shareArticle?mini=true&url=http://oregonstate.edu/~zhanzhid/about/" target="_blank" class="circle boxshadow">
    <i class="socicon-linkedin"></i>
  </a>
  
  <a href="http://instagram.com" target="_blank" class="circle boxshadow">
    <i class="socicon-instagram"></i>
  </a>
</nav>
<br class="clear" />
      <ul class="aligncenter">
        <li><?php echo 'Updated ' . date('F j, Y',filemtime($_SERVER['SCRIPT_FILENAME'])) ?></li>
        <li><cite>&copy; 2018-<?php echo date('Y').' '.$author; ?></cite></li> 
        <li><a href="#top">Top</a></li>
    </ul>
</footer>
    
<!-- scripts -->  
  <script>
  var sound = new Audio();
  sound.src = 'media/ding.mp3';
</script>
 <script src='tablesort.min.js'></script>
<script>new Tablesort(document.getElementById('sort'));</script>
 <script src="smoothscroll.js"></script>
 <script>
    var bgArray = ['img/background-0.jpg'],
            selectBG = bgArray[Math.floor(Math.random() * bgArray.length)];
    if (document.documentElement.clientWidth > 1079) {
        document.body.style.backgroundImage = 'url(' + selectBG + ')';
    }
</script>
 <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>    
<script src="jquery.captionate.js"></script>    
<script>
    // North Krimsly: wait until images have all loaded so we can properly get their width
    $(window).load(function(){  
       $('img.caption').captionate();
    });
</script>
<!--prefix-free--> 
</body> 
</html>