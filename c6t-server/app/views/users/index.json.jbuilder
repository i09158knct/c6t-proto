json.array!(@users) do |user|
  json.partial! user
  json.url user_url(user, format: :json)
end
