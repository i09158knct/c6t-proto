json.array!(@routes) do |route|
  json.extract! route, :name, :achievement_count, :played_count, :start_location, :description, :user_id
  json.url route_url(route, format: :json)
end
