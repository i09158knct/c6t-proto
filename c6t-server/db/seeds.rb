# encoding: utf-8

RANDOM_SEED = 0
RANDOM = Random.new(RANDOM_SEED)
BASE_TIME = Time.new(2013, 9, 1)

def random_location_string
  latitude = 34.0 + RANDOM.rand
  longitude = 134.0 + RANDOM.rand
  "#{latitude},#{longitude}"
end

def random_recently_time
  random_offset = 1000 * 60 * 60 * 24 * 30 * 2 * (RANDOM.rand - 0.5)
  Time.at(BASE_TIME.to_i + random_offset)
end

def create_seed_user(number)
  User.create({
    name: "#{number}郎",
    area: "東京#{number}",
  })
end

def create_seed_route(user, number)
  Route.create({
    name: "#{user.name}のルート (#{number})",
    start_location: random_location_string,
    description: "日本周辺を探検する。\n期間長め",
    user: user,
    quests: (1..5).map {|i|
      Quest.new({
        location: random_location_string,
        pose: "ピース",
        mission: "車を撮る",
      })
    },
  })
end

def create_seed_exploration(route, number)
  Exploration.create({
    start_time: random_recently_time,
    route: route,
    host: route.user,
    description: "#{route.user.name}のテストデータ (#{number})",
  })
end

users = (1..5).map {|i|
  create_seed_user(i)
}

users.each {|user|
  (1..3).each {|number|
    create_seed_route(user, number)
  }
}

users.each {|user|
  user.routes.each {|route|
    (1..3).each {|number|
      create_seed_exploration(route, number)
    }
  }
}

