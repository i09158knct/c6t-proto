json.array!(@users) do |user|
  json.extract! user, :name, :area
  json.url user_url(user, format: :json)
end
