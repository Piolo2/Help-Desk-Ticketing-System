describe("Help Desk Ticketing System E2E Tests", () => {
  beforeEach(() => {
    // Navigate to local Tomcat server root
    cy.visit("/");
  });

  it("should show validation errors on incorrect login credentials", () => {
    // Clear and input invalid details
    cy.get("#login-email").clear().type("invalid@example.com");
    cy.get("#login-password").clear().type("wrongpassword");
    cy.get("#login-btn").click();

    // Verify error message is shown (via toast)
    cy.get(".toast.error").should("be.visible").and("contain", "Invalid credentials");
  });

  it("should log in successfully and perform full ticketing workflow", () => {
    // 1. Login
    cy.get("#login-email").clear().type("admin@gmail.com");
    cy.get("#login-password").clear().type("password123");
    cy.get("#login-btn").click();

    // Verify dashboard is shown
    cy.get("#dashboard-screen").should("be.visible");
    cy.get("#profile-email").should("contain", "admin@gmail.com");

    // 2. Submit a Ticket
    const ticketTitle = `Cypress Test Issue #${Date.now()}`;
    const ticketDesc = "This is a test ticket generated automatically by Cypress E2E test framework.";
    const userEmail = "cypress.tester@example.com";

    cy.get("#ticket-title").type(ticketTitle);
    cy.get("#ticket-desc").type(ticketDesc);
    cy.get("#ticket-email").type(userEmail);
    cy.get("#submit-ticket-btn").click();

    // Verify success toast appears
    cy.get(".toast.success").should("be.visible").and("contain", "Ticket Submitted Successfully!");

    // Verify the ticket is displayed in the active list
    cy.get("#ticket-list-container")
      .contains(".ticket-title", ticketTitle)
      .should("be.visible");

    // 3. Select the ticket and verify operational details
    cy.get("#ticket-list-container")
      .contains(".ticket-card", ticketTitle)
      .click();

    cy.get("#details-view").should("be.visible");
    cy.get("#detail-title").should("contain", ticketTitle);

    // 4. Assign Technician
    cy.get("#assign-tech").select("Sarah Jenkins");
    cy.get("#assign-btn").click();

    // Verify toast & state update
    cy.get(".toast.success").should("be.visible").and("contain", "Assigned successfully to Sarah Jenkins");
    cy.get("#ticket-list-container")
      .contains(".ticket-card", ticketTitle)
      .find(".ticket-status")
      .should("contain", "ASSIGNED: SARAH_JENKINS");

    // 5. Update Status manually
    cy.get("#update-status").select("RESOLVED");
    cy.get("#update-status-btn").click();

    // Verify toast & status badge update
    cy.get(".toast.success").should("be.visible").and("contain", "Status updated to RESOLVED");
    cy.get("#ticket-list-container")
      .contains(".ticket-card", ticketTitle)
      .find(".ticket-status")
      .should("contain", "RESOLVED");

    // 6. Log out
    cy.get("#logout-btn").click();

    // Verify back to login screen
    cy.get("#auth-screen").should("be.visible");
    cy.get("#dashboard-screen").should("not.be.visible");
  });
});
