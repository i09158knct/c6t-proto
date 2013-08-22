# encoding: utf-8

# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

users = (1..10).map {|i|
  User.create({
    name: "#{i}郎",
    area: "東京#{i}",
  })
}

route = Route.create({
  name: "#{users[0].name}'s route in #{users[0].area}",
  start_location: "34.0,134.0",
  description: "日本周辺を探検する。\n期間長め",
  user: users[0],
  quests: (1..5).map {|i|
    Quest.new({
      location: "#{34.0 + i},#{134.0 + i}",
      pose: "ピース",
      mission: "車を撮る",
    })
  },
})

Exploration.create({
  start_time: DateTime.now,
  route: route,
  host: users[0],
  description: "test exploration.",
})
