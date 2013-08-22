json.extract! route,
  :name,
  :achievement_count,
  :played_count,
  :start_location,
  :description,
  :created_at

json.quests do |json|
  json.array!(route.quests) do |quest|
    json.extract! quest,
      :location,
      :pose,
      :mission,
      :photo
  end
end

json.user do |json|
  json.partial! route.user
end
