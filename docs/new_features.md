---
layout: page
title: JDK New Features
permalink: /features/
---


<ul class="post-list">
{% assign jdks = site.data.jdks.JDK | sort: 'year' | reverse %}
{% for jdk in jdks %}
    <li>
        <h4 class="post-list-heading"> 
            <a href="https://jdk.java.net/{{ jdk.version }}" target="_blank">JDK {{ jdk.version }}</a>
            <a href="https://www.oracle.com/java/technologies/javase/{{ jdk.version }}-relnote-issues.html" target="_blank">JDK {{ jdk.version }}</a>
            <p class="post-meta">First release year: {{ jdk.year }} 
            {% if jdk.LTS %}- LTS version{% endif %}</p>
            
        </h4>
        <ul> 
            {% for feature in jdk.features %}
            <li>
                {% if feature.JEP %} 
                    <a href= "https://openjdk.org/jeps/{{feature.JEP}}" target="_blank"> JEP {{ feature.JEP }}: </a>
                {% endif %}   
                
                {{ feature.description }}
                
                {% assign related_posts = site.posts | where_exp: "item", "item.JEPs contains feature.JEP" %}
                {% if related_posts.size > 0 %}
                    {% for post in related_posts %}
                        <a href="{{post.url | relative_url }}">-<i>{{post.title}}</i></a>
                    {% endfor %} 
                {% endif %}   
            </li>
            {% endfor %}
        </ul>
    </li>
    
{% endfor %}
</ul>