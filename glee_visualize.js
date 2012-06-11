var show = [];
var vis = new pv.Panel()
  .width(1250)
  .height(500)
  .bottom(90);
var files = [];
var links = [];
var nodes = [];
function generateFiles() {
  files = [];
  for(var i = 0; i < filecounts.length; i++)
  {
    files.push(filecounts[i].file.replace("/Users/wxstamper/github/iron/client/public/","").replace(".coffee",""));
  }
}
function generateLinks() {
  links = [];
  for(var i = 0; i < filecounts.length; i++)
  {
    for(var j = 0; j < filecounts[i].counts.length; j++)
    {
      var s = i;
      var t = files.indexOf(filecounts[i].counts[j].path);
      var v = Number(filecounts[i].counts[j].count);
      if(v==0)
      {
        console.log(files[i].substring(files[i].lastIndexOf("/")+1) + " imports " + filecounts[i].counts[j].name + " for no reason...");
      }
      else if(t!=-1)
      {
        links.push({"source":s, "target":t, "value":v});
      }
    }
  }
}
function generateNodes() {
  nodes = [];
  for(var i = 0; i < files.length; i++)
  {
    var temp = files[i].replace("_view_controller","");
    nodes.push({"nodeValue":temp.substring(temp.lastIndexOf("/")+1)});
  }
}
generateFiles();
generateLinks();
generateNodes();
var arc = null;
var nodeMark = null;
var labels = null;
var linkMarks = null;
function drawArcs() {
  arc = vis.add(pv.Layout.Arc)
    .nodes(nodes)
    .links(links)
    .sort(function(a,b) a.nodeValue > b.nodeValue
        ?1
        :-1)
}
drawArcs();
linkMarks = arc.link.add(pv.Line)
.strokeStyle(function(d) {
  return (show.indexOf(d.index)==-1)?"rgba(0, 0, 0, 0)":"rgba(0, 0, 255, 0.5)"
  })
labels = arc.label.add(pv.Label)
nodeMarks = arc.node.add(pv.Dot)
  .size(function(d) d.linkDegree + 10 )
  .fillStyle(pv.Colors.category19().by(function(d) d.group))
  .strokeStyle(function() this.fillStyle().darker())
.event("click", function() {
    if(show.indexOf(this.index)==-1)
      show.push(this.index);
    else
      show.splice(show.indexOf(this.index),1);
    linkMarks.render();
  });
vis.render();
