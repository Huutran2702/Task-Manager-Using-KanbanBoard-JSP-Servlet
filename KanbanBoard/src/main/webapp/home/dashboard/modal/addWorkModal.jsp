<%--Modal add new work--%>
<div class="modal fade" id="addWorkModal">
    <div class="modal-dialog modal-lg  modal-dialog-centered">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title"> Add new work</h4>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <div class="row">
                    <div>
                        <label for="newWorkName">Full name</label>
                        <input type="text" class="form-control" placeholder="" name="newWorkName" id="newWorkName">
                    </div>
                </div>
                <div class="row">
                    <div>
                        <label for="newWorkDescription">Full name</label>
                        <input type="text" class="form-control" placeholder="" name="newWorkDescription"
                               id="newWorkDescription">
                    </div>
                </div>
                <div class="row">
                    <div>
                        <label for="new">Phone</label>
                        <input type="tel" class="form-control" placeholder="" name="phone">
                    </div>
                </div>

            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">Save changes</button>
            </div>
        </div>
    </div>
</div>
<%--End modal add new work--%>