class RoutesController < ApplicationController
  protect_from_forgery except: [:put_quest_image]

  before_action :set_route, only: [
    :show, :edit, :update, :destroy, :put_quest_image,
  ]

  # GET /routes
  # GET /routes.json
  def index
    if params[:query].present? && params[:for].present?
      query = params[:query]
      case params[:for]
      when 'route_id'
        @routes = Route
          .where(route_id: query)

      when 'description'
        @routes = Route
          .where('description like ?', "%#{query}%")

      when 'user_name'
        @routes = Route
          .joins(:user)
          .where(users: {name: query})

      when 'location'
        # TODO
        raise "-- not implemented (location) --"

      else
        @routes = Route.all
      end

    else
      @routes = Route.all
    end

    sort_column = 'created_at'
    if params[:sort].present?
      case params[:sort]
      when 'played_count'
        sort_column = 'played_count'

      when 'achievement_count'
        sort_column = 'achievement_count'

      end
    end

    order = (params[:order] == 'asc') ? 'ASC' : 'DESC'
    @routes.order!("#{sort_column} #{order}")
  end

  # GET /routes/1
  # GET /routes/1.json
  def show
  end

  # GET /routes/new
  def new
    @route = Route.new
  end

  # GET /routes/1/edit
  def edit
  end

  # POST /routes
  # POST /routes.json
  def create
    quests_params = params.permit(quests: [:mission, :pose, :location])[:quests]
    quests = quests_params.map {|quest_params|
      Quest.new(quest_params)
    }

    route_params = params.permit(
      :name,
      :achievement_count,
      :played_count,
      :start_location,
      :description
    )

    @route = Route.new({
      user_id: params[:user][:id],
      # user_id: 1,
      quests: quests,
    }.merge(route_params))

    respond_to do |format|
      if @route.save
        format.html { redirect_to @route, notice: 'Route was successfully created.' }
        format.json { render action: 'show', status: :created, location: @route }
      else
        format.html { render action: 'new' }
        format.json { render json: @route.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /routes/1
  # PATCH/PUT /routes/1.json
  def update
    respond_to do |format|
      if @route.update(route_params)
        format.html { redirect_to @route, notice: 'Route was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @route.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /routes/1
  # DELETE /routes/1.json
  def destroy
    @route.destroy
    respond_to do |format|
      format.html { redirect_to routes_url }
      format.json { head :no_content }
    end
  end

  def put_quest_image
    # image_file = request.body
    quest_number = params[:quest_number].to_i
    image_file = params[:image]

    file_path = @route.save_quest_image(quest_number, image_file)
    actual_path = file_path.match(/public\/(routes\/.*)$/)[1]
    photo_url = 'http://' + request.host_with_port + '/' + actual_path
    @route.quests[quest_number].photo = photo_url
    @route.quests[quest_number].save

    render template: 'routes/show.json.jbuilder'
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_route
      @route = Route.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def route_params
      params.require(:route).permit(:name, :achievement_count, :played_count, :start_location, :description, :user_id)
    end
end
